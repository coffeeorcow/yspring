package org.springframework.test.common.event;

import org.springframework.context.ApplicationListener;

/**
 * @author derekyi
 * @date 2020/12/5
 */
public class CustomEventListener implements ApplicationListener<org.springframework.test.common.event.CustomEvent> {

	@Override
	public void onApplicationEvent(org.springframework.test.common.event.CustomEvent event) {
		System.out.println(this.getClass().getName());
	}
}
