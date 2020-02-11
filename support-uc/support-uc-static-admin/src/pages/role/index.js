import React, { PureComponent, Fragment } from 'react';
import { connect } from "react-redux";
import { Tabs, message } from 'antd';
import styles from './index.less';

class Login extends PureComponent {

  constructor(props) {
    super(props);
  }

  render() {
    const { way } = this.props;
    const { switchLoginWay } = this.props;
    return (
      <div className={styles.homePage}>
        Role
      </div>
    );
  }

}

const mapStateToProps = (state) => ({
  way: state.getIn(['home', 'way'])
});

const mapDispatchToProps = (dispatch) => ({
  
});

export default connect(mapStateToProps, mapDispatchToProps)(Login);
