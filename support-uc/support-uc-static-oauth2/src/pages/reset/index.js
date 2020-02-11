import React from 'react';
import { connect } from "react-redux";
import { Tabs } from 'antd';
import Step1 from './components/Step1';
import Step2 from './components/Step2';
import styles from './index.less';

const Reset = (props) => {
  const { step } = props;
  return (
    <div className={styles.resetPage}>
      <div className={styles.resetPosition}>
        <div className={styles.resetWrapper}>
          <Tabs activeKey={step}>
            <Tabs.TabPane tab="1.验证邮箱" key="1">
              <Step1 />
            </Tabs.TabPane>
            <Tabs.TabPane tab="2.重置密码" key="2">
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
  step: state.get('reset').get('step')
});

const mapDispatchToProps = (dispatch) => ({
  
});

export default connect(mapStateToProps, mapDispatchToProps)(Reset);
