package com.linkare.rec.impl.newface.component;

import org.jdesktop.application.Application;

import com.linkare.rec.impl.newface.ReCApplication;

/**
 * @author João Florindo
 */
public class TestFormComponents extends ReCApplication {

    public TestFormComponents() {
        // Required for AppFramework instatiation
    }

	@Override
	protected void showView() {
		DefaultDialog<FormComponents> dialog = new DefaultDialog<FormComponents>(new FormComponents());
		dialog.setVisible(true);
        System.exit(0);
	}

    public static void main(String[] args) {
		Application.launch(TestFormComponents.class, args);
	}

}