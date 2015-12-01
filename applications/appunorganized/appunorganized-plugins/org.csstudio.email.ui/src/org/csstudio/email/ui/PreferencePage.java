/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.email.ui;

import org.csstudio.email.Preferences;
import org.csstudio.security.ui.PasswordFieldEditor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/** Preference Page
 *  Registered in plugin.xml
 *  @author Kay Kasemir
 */
public class PreferencePage extends FieldEditorPreferencePage
implements IWorkbenchPreferencePage
{
    /** Initialize to use 'instance' scope (install location)
     *  and not workspace!
     */
    public PreferencePage()
    {
        super(GRID);
        // Note prefs are in the basic EMail plugin, not in the GUI plugin!
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE,
                org.csstudio.email.Activator.ID));
    }

    @Override
    public void init(IWorkbench workbench)
    {
        // NOP
    }

    @Override
    protected void createFieldEditors()
    {
        setMessage(Messages.Preferences);
        final Composite parent = getFieldEditorParent();
        addField(new StringFieldEditor(Preferences.SMTP_HOST, Messages.SMTPHost, parent));
        addField(new IntegerFieldEditor(Preferences.SMTP_PORT, Messages.SMTPPort, parent));
        addField(new BooleanFieldEditor(Preferences.SMTP_AUTH, Messages.SMTPAuth, parent));
        addField(new BooleanFieldEditor(Preferences.SMTP_STARTTLS, Messages.SMTPStartTLS, parent));
        addField(new BooleanFieldEditor(Preferences.SMTP_SSL, Messages.SMTPSSL, parent));
        addField(new StringFieldEditor(Preferences.SMTP_USERNAME, Messages.SMTPUsername, parent));
        addField(new PasswordFieldEditor(org.csstudio.email.Activator.ID, Preferences.SMTP_PASSWORD, Messages.SMTPPassword, parent));
    }
}
