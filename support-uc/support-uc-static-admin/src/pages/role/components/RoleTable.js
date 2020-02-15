import React, { Fragment } from 'react';
import { Table, Input, Button, Modal, Select, Spin } from 'antd';
import { connect } from 'react-redux';
import { fromJS } from 'immutable';
import { actionCreators } from '../store';
import dayjs from 'dayjs';
import styles from './RoleTable.less';

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

const mapStateToProps = (state) => ({
  roleView: state.getIn(['role', 'roleView']),
  editView: state.getIn(['role', 'editView']),
  loading: state.getIn(['role', 'tableLoading']),
  permissionModalVisible: state.getIn(['role', 'permissionModalVisible']),
  permissionModalRoleId: state.getIn(['role', 'permissionModalRoleId']),
  permissionModalNickname: state.getIn(['role', 'permissionModalNickname']),
  permissionModalOwnedPermissions: state.getIn(['role', 'permissionModalOwnedPermissions']),
  permissionModalSearchPermissions: state.getIn(['role', 'permissionModalSearchPermissions'])
});

const mapDispatchToProps = (dispatch) => ({
  edit(editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), editable: true});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  cancel(editView, editItem, roleView) {
    const id = editItem.get('id');
    if (Number(id) < 0) {
      dispatch(actionCreators.setEditView(editView.delete(id)));
    } else {
      const rollback = roleView.get(editItem.get('id'));
      dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), rollback)));
    }
  },
  save(editView, editItem, roleView) {
    dispatch(actionCreators.save(editView, editItem, roleView));
  },
  changeName(e, editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), name: e.target.value});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  showPermissionModal(editItem) {
    dispatch(actionCreators.showPermissionModal({ roleId: editItem.get('id'), nickname: editItem.get('nickname') }));
  },
  cancelPermissionModal() {
    dispatch(actionCreators.setPermissionModalVisible(false));
  },
  searchPermissions(value) {
    dispatch(actionCreators.searchPermissions(value));
  },
  changePermission(newValues, owneds, roleId, permissionModalOwnedPermissions) {
    if (owneds.length == newValues.length) { return; }
    if (owneds.length > newValues.length) {
      // 删除
      dispatch(actionCreators.revokePermission({ roleId, value: findReduced(owneds, newValues), permissionModalOwnedPermissions }));
    } else {
      // 新增
      dispatch(actionCreators.grantPermission({ roleId, value: findReduced(newValues, owneds), permissionModalOwnedPermissions }));
    }
  },
  removeRole({ roleId, editView, roleView }) {
    Modal.confirm({
      content: '确定要删除该角色么？',
      onOk() {
        return new Promise((resolve, reject) => {
          dispatch(actionCreators.removeRole({ roleId, editView, roleView , resolve }));
        });
      }
    });
  }
});

const RawEditableCell = (props) => {
  const { text, record, index, editView } = props;
  const { changeName } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  if (editable) {
    return <Input onChange={(e) => { changeName(e, editView, editItem) }} value={text} />;
  } else {
    return <span>{ text }</span>;
  }
}

const EditableCell = connect(mapStateToProps, mapDispatchToProps)(RawEditableCell);

const RawPermissionModal = (props) => {
  const { permissionModalVisible, permissionModalRoleId, permissionModalNickname, permissionModalOwnedPermissions, permissionModalSearchPermissions } = props;
  const { cancelPermissionModal, searchPermissions, changePermission } = props;
  const owneds = [];
  permissionModalOwnedPermissions.map((item, index) => {
    owneds.push({ key: item.get('id'), label: item.get('name') });
  });
  const options = [];
  permissionModalSearchPermissions.map((item, index) => {
    options.push(<Select.Option key={item.get('id')} >{ item.get('name') }</Select.Option>);
  });
  return (
    <Modal
      visible={permissionModalVisible}
      title="使用下方搜索框授予或收回权限"
      closable={true}
      maskClosable={false}
      footer={null}
      onCancel={cancelPermissionModal}
    >
      <Select
        mode="multiple"
        style={{ width: '100%' }}
        placeholder="请输入权限名称前缀"
        filterOption={false}
        value={owneds}
        onDropdownVisibleChange={() => { searchPermissions(''); }}
        onSearch={(value) => { searchPermissions(value); }}
        onChange={(value) => { changePermission(value, owneds, permissionModalRoleId, permissionModalOwnedPermissions); }}
        labelInValue
      >
        { options }
      </Select>
    </Modal>
  );
}

const PermissionModal = connect(mapStateToProps, mapDispatchToProps)(RawPermissionModal);

const RawOperationCell = (props) => {
  const { text, record, index, editView, roleView } = props;
  const { edit, cancel, save, showPermissionModal, removeRole } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  const roleId = editItem.get('id');
  if (editable) {
    return (
      <Fragment>
        <Button icon="close-circle"onClick={() => { cancel(editView, editItem, roleView); }}>取消</Button>
        <Button className={styles.rightBtn} icon="check-circle" onClick={() => { save(editView, editItem, roleView); }}>保存</Button>
      </Fragment>
    );
  } else {
    return (
      <Fragment>
        <Button icon="edit" onClick={() => { edit(editView, editItem); }}>编辑</Button>
        <Button className={styles.rightBtn}  icon="safety" onClick={() => { showPermissionModal(editItem); }} >权限</Button>
        <Button className={styles.rightBtn}  icon="delete" onClick={() => { removeRole({ roleId, editView, roleView }); }} >删除</Button>
      </Fragment>
    );
  }

}

const OperationCell = connect(mapStateToProps, mapDispatchToProps)(RawOperationCell);

const columns = [
  {
    title: '角色ID',
    width: 70,
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '名称',
    width: 100,
    dataIndex: 'name',
    key: 'name',
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
    width: 342,
    render: (text, record, index) => <OperationCell text={text} record={record} index={index} />,
  }
];

const RoleTable = (props) => {
  const { editView, loading } = props;
  const data = [];

  editView.map((item) => {
    if (Number(item.get('id')) < 0) {
      // 新增部分
      data.push({
        key: item.get('id'),
        id: '',
        name: item.get('name'),
        createdTime: '',
        modifiedTime: ''
      });
    } else {
      data.push({
        key: item.get('id'),
        id: item.get('id'),
        name: item.get('name'),
        createdTime: dayjs(item.get('createdTime')).format('YYYY-MM-DD HH:mm:ss'),
        modifiedTime: dayjs(item.get('modifiedTime')).format('YYYY-MM-DD HH:mm:ss')
      });
    }
  });

  return (
    <Fragment>
      <Table
        className={styles.container}
        columns={columns}
        dataSource={data}
        pagination={false}
        loading={loading}
        scroll={{ x: 1000 }}
       />
       <PermissionModal />
    </Fragment>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(RoleTable);
