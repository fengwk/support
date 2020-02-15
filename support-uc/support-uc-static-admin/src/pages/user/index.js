import React from 'react';
import UserSearch from './components/UserSearch';
import UserTable from './components/UserTable';
import UserPagination from './components/UserPagination';
import styles from './index.less';

const User = (props) => {
  return (
    <div className={styles.container}>
      <UserSearch />
      <UserTable />
      <UserPagination />
    </div>
  );
}

export default User;
