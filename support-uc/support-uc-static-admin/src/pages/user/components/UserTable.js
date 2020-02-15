import React, { Fragment } from 'react';
import { Table, Input, Button, Modal, Select, Spin } from 'antd';
import { connect } from 'react-redux';
import { fromJS } from 'immutable';
import { actionCreators } from '../store';
import dayjs from 'dayjs';
import styles from './UserTable.less';

const mapStateToProps = (state) => ({
  userView: state.getIn(['user', 'userView']),
  editView: state.getIn(['user', 'editView']),
  loading: state.getIn(['user', 'tableLoading']),
  roleModalVisible: state.getIn(['user', 'roleModalVisible']),
  roleModalUserId: state.getIn(['user', 'roleModalUserId']),
  roleModalNickname: state.getIn(['user', 'roleModalNickname']),
  roleModalOwnedRoles: state.getIn(['user', 'roleModalOwnedRoles']),
  roleModalSearchRoles: state.getIn(['user', 'roleModalSearchRoles'])
});

function findReduced(manys, lesses) {
  for (const owend of manys) {
    const key = owend.key;
    let fined = false;
    for (const newValue of lesses) {
      if (newValue.key === key) {
        fined = true;
        break;
      }
    }
    if (!fined) {
      return owend;
    }
  }
}

const mapDispatchToProps = (dispatch) => ({
  edit(editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), editable: true});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  cancel(editView, editItem, userView) {
    const rollback = userView.get(editItem.get('id'));
    dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), rollback)));
  },
  save(editView, editItem, userView) {
    dispatch(actionCreators.save(editView, editItem, userView));
  },
  changeNickname(e, editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), nickname: e.target.value});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  showRoleModal(editItem) {
    dispatch(actionCreators.showRoleModal({ userId: editItem.get('id'), nickname: editItem.get('nickname') }));
  },
  cancelRoleModal() {
    dispatch(actionCreators.setRoleModalVisible(false));
  },
  searchRoles(value) {
    dispatch(actionCreators.searchRoles(value));
  },
  changeRole(newValues, owneds, userId, roleModalOwnedRoles) {
    if (owneds.length == newValues.length) { return; }
    if (owneds.length > newValues.length) {
      // 删除
      dispatch(actionCreators.revokeRole({ userId, value: findReduced(owneds, newValues), roleModalOwnedRoles }));
    } else {
      // 新增
      dispatch(actionCreators.grantRole({ userId, value: findReduced(newValues, owneds), roleModalOwnedRoles }));
    }
  }
});

const RawEditableCell = (props) => {
  const { text, record, index, editView } = props;
  const { changeNickname } = props;
  const editItem = editView.get(record.id);
  const editable = editItem.get('editable');
  if (editable) {
    return <Input onChange={(e) => { changeNickname(e, editView, editItem) }} value={text} />;
  } else {
    return <span>{ text }</span>;
  }
}

const EditableCell = connect(mapStateToProps, mapDispatchToProps)(RawEditableCell);

const RawRoleModal = (props) => {
  const { roleModalVisible, roleModalUserId, roleModalNickname, roleModalOwnedRoles, roleModalSearchRoles } = props;
  const { cancelRoleModal, searchRoles, changeRole } = props;
  const owneds = [];
  roleModalOwnedRoles.map((item, index) => {
    owneds.push({ key: item.get('id'), label: item.get('name') });
  });
  const options = [];
  roleModalSearchRoles.map((item, index) => {
    options.push(<Select.Option key={item.get('id')} >{ item.get('name') }</Select.Option>);
  });
  return (
    <Modal
      visible={roleModalVisible}
      title="使用下方搜索框授予或收回角色"
      closable={true}
      maskClosable={false}
      footer={null}
      onCancel={cancelRoleModal}
    >
      <Select
        mode="multiple"
        style={{ width: '100%' }}
        placeholder="请输入角色名称前缀"
        filterOption={false}
        value={owneds}
        onDropdownVisibleChange={() => { searchRoles(''); }}
        onSearch={(value) => { searchRoles(value); }}
        onChange={(value) => { changeRole(value, owneds, roleModalUserId, roleModalOwnedRoles); }}
        labelInValue
      >
        { options }
      </Select>
    </Modal>
  );
}

const RoleModal = connect(mapStateToProps, mapDispatchToProps)(RawRoleModal);

const RawOperationCell = (props) => {
  const { text, record, index, editView, userView } = props;
  const { edit, cancel, save, showRoleModal } = props;
  const editItem = editView.get(record.id);
  const editable = editItem.get('editable');
  const userId = editItem.get('id');
  if (editable) {
    return (
      <Fragment>
        <Button icon="close-circle" onClick={() => { cancel(editView, editItem, userView); }}>取消</Button>
        <Button className={styles.rightBtn} icon="check-circle" onClick={() => { save(editView, editItem, userView); }}>保存</Button>
      </Fragment>
    );
  } else {
    return (
      <Fragment>
        <Button icon="edit" onClick={() => { edit(editView, editItem); }}>编辑</Button>
        <Button className={styles.rightBtn} icon="team" onClick={() => { showRoleModal(editItem); }} >角色</Button>
      </Fragment>
    );
  }

}

const OperationCell = connect(mapStateToProps, mapDispatchToProps)(RawOperationCell);

const columns = [
  {
    title: '用户ID',
    width: 70,
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '邮箱',
    width: 200,
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: '昵称',
    width: 200,
    dataIndex: 'nickname',
    key: 'nickname',
    render: (text, record, index) => <EditableCell text={text} record={record} index={index} editable={true} />,
  },
  {
    title: '创建时间',
    width: 170,
    dataIndex: 'createdTime',
    key: 'createdTime',
  },
  {
    title: '修改时间',
    width: 170,
    dataIndex: 'modifiedTime',
    key: 'modifiedTime',
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 250,
    render: (text, record, index) => <OperationCell text={text} record={record} index={index} />,
  }
];

const UserTable = (props) => {
  const { editView, loading } = props;
  const data = [];

  editView.map((item) => {
    data.push({
      key: item.get('id'),
      id: item.get('id'),
      email: item.get('email'),
      nickname: item.get('nickname'),
      createdTime: dayjs(item.get('createdTime')).format('YYYY-MM-DD HH:mm:ss'),
      modifiedTime: dayjs(item.get('modifiedTime')).format('YYYY-MM-DD HH:mm:ss')
    });
  });

  return (
    <Fragment>
      <Table
        className={styles.container}
        columns={columns}
        rowKey={record => record.id}
        dataSource={data}
        pagination={false}
        loading={loading}
        scroll={{ x: 1200 }}
       />
       <RoleModal />
    </Fragment>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(UserTable);
