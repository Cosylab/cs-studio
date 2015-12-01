/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.email;

import org.csstudio.security.preferences.SecurePreferences;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;

/** Access to preference settings.
 *
 *  See preferences.ini for details on the available settings
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class Preferences
{
    final public static String SMTP_USERNAME = "smtp_username";
    final public static String SMTP_PASSWORD = "smtp_password";
    final public static String SMTP_HOST = "smtp_host";
    final public static String SMTP_SENDER = "smtp_sender";
    final public static String SMTP_PORT = "smtp_port";
    final public static String SMTP_AUTH = "smtp_auth";
    final public static String SMTP_STARTTLS = "smtp_starttls";
    final public static String SMTP_SSL = "smtp_ssl";

    /** @return SMTP URL */
    public static String getSMTP_Host()
    {
        String host = "localhost";
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            host = service.getString(Activator.ID, SMTP_HOST, host, null);
        return host;
    }

    public static int getSMTP_Port()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getInt(Activator.ID, SMTP_PORT, 25, null);
        return 25;
    }
    
    public static boolean isSMTPSSL()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getBoolean(Activator.ID, SMTP_SSL, false, null);
        return false;
    }
    
    public static boolean isSMTPAuthEnabled()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getBoolean(Activator.ID, SMTP_AUTH, false, null);
        return false;
    }
    
    public static boolean isSMTPStartTLSEnabled()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getBoolean(Activator.ID, SMTP_STARTTLS, false, null);
        return false;
    }
    
    /** @return SMTP username */
    public static String getSMTP_Username()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getString(Activator.ID, SMTP_USERNAME, null, null);
        return null;
    }
    
    /** @return SMTP password */
    public static String getSMTP_Password()
    {
        String password = SecurePreferences.get(Activator.ID, SMTP_PASSWORD, null);
        if (password == null) {
            final IPreferencesService service = Platform.getPreferencesService();
            if (service != null)
                return service.getString(Activator.ID, SMTP_PASSWORD, null, null);
        }
        return password;
    }
    
    /** @return SMTP URL */
    public static String getSMTP_Sender()
    {
        final IPreferencesService service = Platform.getPreferencesService();
        if (service != null)
            return service.getString(Activator.ID, SMTP_SENDER, null, null);
        return null;
    }
}
