import React, { PureComponent, Fragment } from 'react';
import { Table, Input, Button, Modal, Select, Spin } from 'antd';
import { connect } from 'react-redux';
import { fromJS } from 'immutable';
import { actionCreators } from '../store';
import dayjs from 'dayjs';
import styles from './PermissionTable.less';

const mapStateToProps = (state) => ({
  permissionView: state.getIn(['permission', 'permissionView']),
  editView: state.getIn(['permission', 'editView']),
  loading: state.getIn(['permission', 'tableLoading'])
});

const mapDispatchToProps = (dispatch) => ({
  edit(editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), editable: true});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  cancel(editView, editItem, permissionView) {
    const id = editItem.get('id');
    if (Number(id) < 0) {
      dispatch(actionCreators.setEditView(editView.delete(id)));
    } else {
      const rollback = permissionView.get(editItem.get('id'));
      dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), rollback)));
    }
  },
  save(editView, editItem, permissionView) {
    dispatch(actionCreators.save(editView, editItem, permissionView));
  },
  changeRowInput(e, editView, editItem, colName) {
    const itemObj = { ...editItem.toJS() };
    itemObj[colName] = e.target.value;
    const newItem = fromJS(itemObj);
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  removePermission({ permissionId, editView, permissionView }) {
    Modal.confirm({
      content: '确定要删除该权限么？',
      onOk() {
        return new Promise((resolve, reject) => {
          dispatch(actionCreators.removePermission({ permissionId, editView, permissionView , resolve }));
        });
      }
    });
  }
});

const RawEditableCell = (props) => {
  const { text, record, index, editView, colName } = props;
  const { changeRowInput } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  if (editable) {
    return <Input onChange={(e) => { changeRowInput(e, editView, editItem, colName) }} value={text} />;
  } else {
    return <span>{ text }</span>;
  }
}

const EditableCell = connect(mapStateToProps, mapDispatchToProps)(RawEditableCell);

const RawOperationCell = (props) => {
  const { text, record, index, editView, permissionView } = props;
  const { edit, cancel, save, showPermissionModal, removePermission } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  const permissionId = editItem.get('id');
  if (editable) {
    return (
      <Fragment>
        <Button icon="close-circle"onClick={() => { cancel(editView, editItem, permissionView); }}>取消</Button>
        <Button className={styles.rightBtn} icon="check-circle" onClick={() => { save(editView, editItem, permissionView); }}>保存</Button>
      </Fragment>
    );
  } else {
    return (
      <Fragment>
        <Button icon="edit" onClick={() => { edit(editView, editItem); }}>编辑</Button>
        <Button className={styles.rightBtn}  icon="delete" onClick={() => { removePermission({ permissionId, editView, permissionView }); }} >删除</Button>
      </Fragment>
    );
  }

}

const OperationCell = connect(mapStateToProps, mapDispatchToProps)(RawOperationCell);

const columns = [
  {
    title: '权限ID',
    width: 70,
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '名称',
    width: 100,
    dataIndex: 'name',
    key: 'name',
    render: (text, record, index) => <EditableCell text={text} record={record} index={index} colName="name" editable={true} />,
  },
  {
    title: '路径',
    width: 100,
    dataIndex: 'path',
    key: 'path',
    render: (text, record, index) => <EditableCell text={text} record={record} index={index} colName="path" editable={true} />,
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

const PermissionTable = (props) => {
  const { editView, loading } = props;
  const data = [];

  editView.map((item) => {
    if (Number(item.get('id')) < 0) {
      // 新增部分
      data.push({
        key: item.get('id'),
        id: '',
        name: item.get('name'),
        path: item.get('path'),
        createdTime: '',
        modifiedTime: ''
      });
    } else {
      data.push({
        key: item.get('id'),
        id: item.get('id'),
        name: item.get('name'),
        path: item.get('path'),
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
    </Fragment>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(PermissionTable);
