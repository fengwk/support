import React, { PureComponent, Fragment } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Input, Select, Button } from 'antd';
import { fromJS, OrderedMap } from 'immutable';
import styles from './ClientSearch.less';

const { Search } = Input;

const mapStateToProps = (state) => ({
  searchType: state.getIn(['client', 'searchType']),
  searchValue: state.getIn(['client', 'searchValue']),
  pageNumber: state.getIn(['client', 'pageNumber']),
  pageSize: state.getIn(['client', 'pageSize']),
  editView: state.getIn(['client', 'editView'])
});

const mapDispatchToProps = (dispatch) => ({
  search(param) {
    dispatch(actionCreators.search(param));
  },
  setSearchType(searchType) {
    dispatch(actionCreators.setSearchType(searchType));
  },
  setSearchValue(e) {
    dispatch(actionCreators.setSearchValue(e.target.value));
  },
  addItem(editView) {
    const temp = {};
    const tempId = -(new Date().getTime()) + '';// 使用字符串,Map不支持对负数的get
    temp[tempId] = {
      id: tempId,
      name: '',
      secret: '',
      redirectRules: [],
      accessExpiresIn: 3600,
      refreshExpiresIn: 7200,
      isExclusive: true,
      tokenCountLimit: 1,
      isDisabled: false,
      editable: true,
      createdTime: '',
      modifiedTime: ''
    };
    const newEditView = OrderedMap(fromJS(temp));
    dispatch(actionCreators.setEditView(newEditView.merge(editView)));
  }
});

class ClientSearch extends PureComponent {

  render() {
    const { searchValue, pageNumber, pageSize, editView } = this.props;
    const { search, setSearchValue, addItem } = this.props;
    return (
      <div className={styles.container}>
        <Search
          className={styles.input}
          placeholder="请输入权限名称前缀"
          value={searchValue}
          onChange={setSearchValue}
          onSearch={() => search({ searchValue, pageNumber, pageSize })}
          enterButton />
          <div>
            <Button type="primary" icon="plus-circle" onClick={() => { addItem(editView); }}>新增</Button>
          </div>
      </div>
    );
  }

  componentDidMount() {
    if (this.props.location.state && this.props.location.state.unload) {
      return;
    }
    this.props.search(this.props);
  }

}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(ClientSearch));
