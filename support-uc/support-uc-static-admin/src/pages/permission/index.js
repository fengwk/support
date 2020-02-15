import React from 'react';
import PermissionSearch from './components/PermissionSearch';
import PermissionTable from './components/PermissionTable';
import PermissionPagination from './components/PermissionPagination';
import styles from './index.less';

const Permission = (props) => {
  return (
    <div className={styles.container}>
      <PermissionSearch />
      <PermissionTable />
      <PermissionPagination />
    </div>
  );
}

export default Permission;
