/*
 *  Copyright (c) 2015-2016 Tapglue (https://www.tapglue.com/). All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.tapglue.sdk;

import android.content.Context;

import com.tapglue.sdk.entities.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Observable;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Tapglue.class)
public class TapglueTest {

    private static final String USERNAME = "username";
    private static final String EMAIL = "user@domain.com";
    private static final String PASSWORD = "password";

    @Mock
    User user;
    @Mock
    Configuration configuration;
    @Mock
    Context context;

    @Mock
    RxTapglue rxTapglue;
    @Mock
    Observable<User> userObservable;
    @Mock
    Observable<Void> voidObservable;

    @Mock
    RxWrapper<User> userWrapper;
    @Mock
    RxWrapper<Void> voidWrapper;

    //SUT
    Tapglue tapglue;

    @Before
    public void setUp() throws Exception {
        whenNew(RxTapglue.class).withArguments(configuration, context).thenReturn(rxTapglue);

        tapglue = new Tapglue(configuration, context);
    }

    @Test
    public void usernameLogin() throws Throwable {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.loginWithUsername(USERNAME, PASSWORD)).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.loginWithUsername(USERNAME, PASSWORD), equalTo(user));
    }

    @Test
    public void emailLogin() throws Throwable {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.loginWithEmail(EMAIL, PASSWORD)).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.loginWithEmail(EMAIL, PASSWORD), equalTo(user));
    }

    @Test
    public void logout() throws Throwable {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(voidWrapper);
        when(rxTapglue.logout()).thenReturn(voidObservable);

        tapglue.logout();

        verify(voidWrapper).unwrap(voidObservable);
    }

    @Test
    public void currentUser() throws Throwable {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.getCurrentUser()).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.getCurrentUser(), equalTo(user));
    }

    @Test
    public void createUser() throws Exception {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.createUser(user)).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.createUser(user), equalTo(user));
    }

    @Test
    public void deleteCurrentUser() throws Exception {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(voidWrapper);
        when(rxTapglue.deleteCurrentUser()).thenReturn(voidObservable);

        tapglue.deleteCurrentUser();

        verify(voidWrapper).unwrap(voidObservable);
    }

    @Test
    public void updateCurrentUser() throws Exception {
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.updateCurrentUser(user)).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.updateCurrentUser(user), equalTo(user));
    }

    @Test
    public void retrieveUser() throws Exception {
        String id = "101";
        whenNew(RxWrapper.class).withNoArguments().thenReturn(userWrapper);
        when(rxTapglue.retrieveUser(id)).thenReturn(userObservable);
        when(userWrapper.unwrap(userObservable)).thenReturn(user);

        assertThat(tapglue.retrieveUser(id), equalTo(user));
    }
}
