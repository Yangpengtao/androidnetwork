
/**
 * 登录页面
 * Yang pengtao
 */
public class LoginActivity extends BaseSellerActivity implements CompoundButton.OnCheckedChangeListener {


    private UserPresenter presenter;
    private String username, password;

  
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (currBtn != null) {
            if (!currBtn.getIsfir()) {
                if (currBtn.getMeasuredWidth() > currBtn.getMaxWidth()) {
                    currBtn.postInvalidate();
                }

            }
        }
    }











    private void getToken() {
        presenter.getToken(this, true, username, password, new Action1<TokenBean>() {
            @Override
            public void call(TokenBean tokenBean) {
                LogPrinter.e(TAG, "------" + tokenBean.data.token);
                SpUtil.getInstance().saveToken(LoginActivity.this, tokenBean.data.token);
                SpUtil.getInstance().saveLoginAccount(LoginActivity.this, username);
                sellerLogin();
            }
        });
    }

    private void sellerLogin() {
        LogPrinter.e(TAG, "------进来" );

        presenter.login(this, true, new Action1<UserBean>() {
            @Override
            public void call(UserBean userBean) {
                LogPrinter.e(TAG, "------" + userBean);
                startActivity(cn.com.oomall.kktown.activity.seller.HomeActivity.class);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        scaleA.cancel();
//        rotateAnimation.cancel();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            etPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPass.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
