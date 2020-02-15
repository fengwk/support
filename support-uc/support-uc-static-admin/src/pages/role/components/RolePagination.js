import React from 'react';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Pagination } from 'antd';
import styles from './RolePagination.less';

const mapStateToProps = (state) => ({
  searchType: state.getIn(['role', 'searchType']),
  searchValue: state.getIn(['role', 'searchValue']),
  pageNumber: state.getIn(['role', 'pageNumber']),
  pageSize: state.getIn(['role', 'pageSize']),
  total: state.getIn(['role', 'total'])
});

const mapDispatchToProps = (dispatch) => ({
  changePage(param) {
    dispatch(actionCreators.changePage(param));
  }
});

const RolePagination = (props) => {
  const { pageNumber, pageSize, total, searchValue } = props;
  const { changePage } = props;
  return (
    <div className={styles.container}>
      <Pagination
        current={pageNumber}
        pageSize={pageSize}
        total={total}
        onChange={(page, pageSize) => {
          changePage({ searchValue, pageNumber: page, pageSize });
        }}
       />
    </div>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(RolePagination);
