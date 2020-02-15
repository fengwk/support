import React, { PureComponent } from 'react';
import { withRouter } from 'react-router-dom';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Input, Select } from 'antd';
import styles from './UserSearch.less';

const { Option } = Select;
const { Search } = Input;

export const convertToSearchParam = ({ searchType, searchValue, pageNumber, pageSize }) => {
  return {
    pageNumber,
    pageSize,
    email: searchType === 'email' ? searchValue : undefined,
    nickname: searchType === 'nickname' ? searchValue : undefined
  };
}

class UserSearch extends PureComponent {

  render() {
    const { searchType, searchValue, pageNumber, pageSize } = this.props;
    const { search, setSearchType, setSearchValue, className } = this.props;
    return (
      <div className={styles.container}>
        <Select className={styles.type} value={searchType} onChange={setSearchType}>
          <Option value="email">邮箱</Option>
          <Option value="nickname">昵称</Option>
        </Select>
        <Search
          className={styles.input}
          placeholder={searchType === 'email' ? '请输入您的邮箱' : '请输入您的昵称'}
          value={searchValue}
          onChange={setSearchValue}
          onSearch={() => search(convertToSearchParam({ searchType, searchValue, pageNumber, pageSize }))}
          enterButton />
      </div>
    );
  }

  componentDidMount() {
    if (this.props.location.state && this.props.location.state.unload) {
      return;
    }
    this.props.search(convertToSearchParam(this.props));
  }

}

const mapStateToProps = (state) => ({
  searchType: state.getIn(['user', 'searchType']),
  searchValue: state.getIn(['user', 'searchValue']),
  pageNumber: state.getIn(['user', 'pageNumber']),
  pageSize: state.getIn(['user', 'pageSize'])
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
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(UserSearch));
