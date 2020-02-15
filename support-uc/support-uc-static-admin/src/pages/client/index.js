import React from 'react';
import ClientSearch from './components/ClientSearch';
import ClientTable from './components/ClientTable';
import ClientPagination from './components/ClientPagination';
import styles from './index.less';

const Client = (props) => {
  return (
    <div className={styles.container}>
      <ClientSearch />
      <ClientTable />
      <ClientPagination />
    </div>
  );
}

export default Client;
