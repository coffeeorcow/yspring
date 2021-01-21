package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream in = resource.getInputStream();
            try {
                doLoadBeanDefinition(in);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    protected void doLoadBeanDefinition(InputStream in) {
        Document document = XmlUtil.readXML(in);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node instanceof Element && BEAN_ELEMENT.equals(node.getNodeName())) {
                // 解析 bean 标签
                Element bean = (Element) node;
                String id = bean.getAttribute(ID_ATTRIBUTE);
                String name = bean.getAttribute(NAME_ATTRIBUTE);
                String className = bean.getAttribute(CLASS_ATTRIBUTE);
                String initMethodName = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
                String destroyMethodName = bean.getAttribute(DESTROY_METHOD_ATTRIBUTE);

                // 加载类
                Class<?> clz;
                try {
                    clz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new BeansException("Cannot find class [" + className + "]");
                }

                // 确定 bean 的名称
                String beanName = id == null ? name : id;
                if (StrUtil.isBlank(beanName)) {
                    beanName = StrUtil.lowerFirst(clz.getName());
                }

                BeanDefinition beanDefinition = new BeanDefinition(clz);
                beanDefinition.setInitMethodName(initMethodName);
                beanDefinition.setDestroyMethodName(destroyMethodName);

                // 组装 bean属性
                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node prop = node.getChildNodes().item(j);
                    if (prop instanceof Element) {
                        if (PROPERTY_ELEMENT.equals(prop.getNodeName())) {
                            Element ele = (Element) prop;
                            String nameAttr = ele.getAttribute(NAME_ATTRIBUTE);
                            String valueAttr = ele.getAttribute(VALUE_ATTRIBUTE);
                            String refAttr = ele.getAttribute(REF_ATTRIBUTE);

                            if (StrUtil.isEmpty(nameAttr)) {
                                throw new BeansException("The name attribute cannot be null or empty");
                            }

                            Object value = valueAttr;
                            if (StrUtil.isNotEmpty(refAttr)) {
                                value = new BeanReference(refAttr);
                            }
                            beanDefinition.getPropertyValues().addProperty(nameAttr, value);
                        }
                    }
                }

                if (getRegistry().containsBeanDefinition(beanName)) {
                    //beanName不能重名
                    throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                }

                // 注册 bean
                getRegistry().registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

}
