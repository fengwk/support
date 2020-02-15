import React from 'react';
import RoleSearch from './components/RoleSearch';
import RoleTable from './components/RoleTable';
import RolePagination from './components/RolePagination';
import styles from './index.less';

const Role = (props) => {
  return (
    <div className={styles.container}>
      <RoleSearch />
      <RoleTable />
      <RolePagination />
    </div>
  );
}

export default Role;
