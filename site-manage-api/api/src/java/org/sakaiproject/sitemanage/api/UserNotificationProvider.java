/**********************************************************************************
 * $URL:$
 * $Id:$
 ***********************************************************************************
 *
 * Copyright (c) 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.sitemanage.api;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;


public interface UserNotificationProvider {

	/**
	 * Configuration parameter for sakai.properties.
	 * Should the added participant emails come from the current user.
	 * The new account emails shouldn't use the current user as we don't want bounces
	 * containing passwords going back to the user who added it.
	 */
	public static final String NOTIFY_FROM_CURRENT_USER = "sitemanage.notifyFromCurrentUser";

	/**
	 * Send an email to newly added user informing password them of their password.
	 * 
	 * @param newUser The newly created user.
	 * @param newUserPassword The password for the newly created user.
	 * @param site The site in which the new user was created.
	 */
	public void notifyNewUserEmail(User newUser, String newUserPassword, Site site);

	/**
	 * Send email notification to added participant indicating they have been added to a site.
	 * 
	 * @param nonOfficialAccount <code>true</code> if the added user is a guest user rather than an official one.
	 * @param user The user who was newly added to the site.
	 * @param site The site to which the user was added as a participant.
	 */
	public void notifyAddedParticipant(boolean nonOfficialAccount, User user, Site site); 
	
}
