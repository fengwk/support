import React from 'react';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Pagination } from 'antd';
import styles from './PermissionPagination.less';

const mapStateToProps = (state) => ({
  searchType: state.getIn(['permission', 'searchType']),
  searchValue: state.getIn(['permission', 'searchValue']),
  pageNumber: state.getIn(['permission', 'pageNumber']),
  pageSize: state.getIn(['permission', 'pageSize']),
  total: state.getIn(['permission', 'total'])
});

const mapDispatchToProps = (dispatch) => ({
  changePage(param) {
    dispatch(actionCreators.changePage(param));
  }
});

const PermissionPagination = (props) => {
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

export default connect(mapStateToProps, mapDispatchToProps)(PermissionPagination);
