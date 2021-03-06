/*
 * Copyright 2012 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.cdi.uis;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@VaadinUI
public class SecondUI extends UI {

    @Inject
    CDIViewProvider viewProvider;

    private Navigator navigator;

    private final static AtomicInteger COUNTER = new AtomicInteger(0);
    private int clickCount;

    @PostConstruct
    public void initialize() {
        COUNTER.incrementAndGet();
        this.clickCount = 0;

    }

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        final Label label = new Label("+SecondUI");
        label.setId("label");
        Button navigate = new Button("button", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator = new Navigator(SecondUI.this, layout);
                navigator.addProvider(viewProvider);
                navigator.navigateTo("danglingView");
            }
        });
        navigate.setId("navigate");
        layout.addComponent(label);
        layout.addComponent(navigate);
        setContent(layout);
    }

    public static int getNumberOfInstances() {
        return COUNTER.get();
    }

    public static void resetCounter() {
        COUNTER.set(0);
    }

}
