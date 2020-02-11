import React, { PureComponent, Fragment } from 'react';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Pagination } from 'antd';
import { convertToSearchParam } from './UserSearch';
import styles from './UserPagination.less';

const UserPagination = (props) => {
  const { searchType, searchValue, pageNumber, pageSize, total } = props;
  const { search } = props;
  return (
    <div className={styles.container}>
      <Pagination 
        current={pageNumber} 
        total={total} 
        onChange={(page, pageSize) => {
          search({ ...convertToSearchParam(props), pageNumber: page, pageSize });
        }} 
       />
    </div>
  );
};


const mapStateToProps = (state) => ({
  searchType: state.getIn(['user', 'searchType']),
  searchValue: state.getIn(['user', 'searchValue']),
  pageNumber: state.getIn(['user', 'pageNumber']),
  pageSize: state.getIn(['user', 'pageSize']),
  total: state.getIn(['user', 'total'])
});

const mapDispatchToProps = (dispatch) => ({
  search(param) {
    dispatch(actionCreators.search(param));
  },
  changePage(param) {
    dispatch(actionCreators.search(param));
  }
});

export default connect(mapStateToProps, mapDispatchToProps)(UserPagination);