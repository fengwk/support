import React from 'react';
import { connect } from "react-redux";
import { actionCreators } from '../store';
import { Pagination } from 'antd';
import styles from './ClientPagination.less';

const mapStateToProps = (state) => ({
  searchType: state.getIn(['client', 'searchType']),
  searchValue: state.getIn(['client', 'searchValue']),
  pageNumber: state.getIn(['client', 'pageNumber']),
  pageSize: state.getIn(['client', 'pageSize']),
  total: state.getIn(['client', 'total'])
});

const mapDispatchToProps = (dispatch) => ({
  changePage(param) {
    dispatch(actionCreators.changePage(param));
  }
});

const ClientPagination = (props) => {
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

export default connect(mapStateToProps, mapDispatchToProps)(ClientPagination);
