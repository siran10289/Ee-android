package com.eeyuva.screens.authentication;

import com.eeyuva.R;
import com.eeyuva.interactor.ApiInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

/**
 * Created by hari on 4/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    LoginContract.View mView;
    LoginPresenterImpl loginPresenter;

    @Inject
    ApiInteractor driverInteractor;

    @Before
    public void setup() {
//        loginPresenter = new LoginPresenterImpl(mView, driverInteractor);
    }

    @Test
    public void testNameShouldNotEmpty() throws Exception {
        loginPresenter.validateUsername("");
        verify(mView).showErrorDialog(R.string.enter_user_name);
    }

    @Test
    public void testNameMustBeGreaterthan2Char() throws Exception {
        loginPresenter.validateUsername("a");
        verify(mView).showErrorDialog(R.string.enter_min_character_user_name);
    }

    @Test
    public void testNameMustBeGreaterthan2Char2() throws Exception {
        loginPresenter.validateUsername("aa");
        verify(mView).showErrorDialog(R.string.enter_min_character_user_name);
    }

    @Test
    public void testPasswordShouldNotEmpty() throws Exception {
        loginPresenter.validatePassword("");
        verify(mView).showErrorDialog(R.string.enter_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char1() throws Exception {
        loginPresenter.validatePassword("a");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char2() throws Exception {
        loginPresenter.validatePassword("aa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char3() throws Exception {
        loginPresenter.validatePassword("aaa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char4() throws Exception {
        loginPresenter.validatePassword("aaaa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char5() throws Exception {
        loginPresenter.validatePassword("aaaaa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char6() throws Exception {
        loginPresenter.validatePassword("aaaaaa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

    @Test
    public void testPasswordMustBeGreaterthan8Char7() throws Exception {
        loginPresenter.validatePassword("aaaaaaa");
        verify(mView).showErrorDialog(R.string.enter_min_character_password);
    }

}