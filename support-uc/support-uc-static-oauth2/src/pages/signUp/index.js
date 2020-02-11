import React from 'react';
import { connect } from "react-redux";
import { Tabs } from 'antd';
import Step1 from './components/Step1';
import Step2 from './components/Step2';
import styles from './index.less';

const SignUp = (props) => {
  const { step } = props;
  return (
    <div className={styles.signUpPage}>
      <div className={styles.signUpPosition}>
        <div className={styles.signUpWrapper}>
          <Tabs activeKey={step}>
            <Tabs.TabPane tab="1.设置邮箱" key="1">
              <Step1 />
            </Tabs.TabPane>
            <Tabs.TabPane tab="2.完善信息" key="2">
              <Step2 />
            </Tabs.TabPane>
          </Tabs>
        </div>
        <div className={styles.signatureWrapper}>
          Power by <span className={styles.signature}>fengwk.com</span>
        </div>
      </div>
    </div>
  );
}

const mapStateToProps = (state) => ({
  step: state.get('signUp').get('step')
});

const mapDispatchToProps = (dispatch) => ({
  
});

export default connect(mapStateToProps, mapDispatchToProps)(SignUp);
