import React, { PureComponent, Fragment } from 'react';
import { Table, Input, Button } from 'antd';
import { connect } from 'react-redux';
import { fromJS } from 'immutable';
import { actionCreators } from '../store';
import dayjs from 'dayjs';
import styles from './UserTable.less';

const mapStateToProps = (state) => ({
  userView: state.getIn(['user', 'userView']),
  editView: state.getIn(['user', 'editView'])
});

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

const RawOperationCell = (props) => {
  const { text, record, index, editView, userView } = props;
  const { edit, cancel, save } = props;
  const editItem = editView.get(record.id);
  const editable = editItem.get('editable');
  if (editable) {
    return (
      <Fragment>
        <Button onClick={() => { cancel(editView, editItem, userView); }}>取消</Button>
        <Button className={styles.rightBtn} onClick={() => { save(editView, editItem, userView); }}>保存</Button>
      </Fragment>
    );
  } else {
    return (
      <Fragment>
        <Button onClick={() => { edit(editView, editItem); }}>编辑</Button>
        <Button className={styles.rightBtn}>角色</Button>
      </Fragment>
    );
  }
  
}

const OperationCell = connect(mapStateToProps, mapDispatchToProps)(RawOperationCell);

const columns = [
  {
    title: '用户ID',
    width: 50,
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '邮箱',
    width: 100,
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: '昵称',
    width: 100,
    dataIndex: 'nickname',
    key: 'nickname',
    render: (text, record, index) => <EditableCell text={text} record={record} index={index} editable={true} />,
  },
  {
    title: '创建时间',
    width: 100,
    dataIndex: 'createdTime',
    key: 'createdTime',
  },
  {
    title: '修改时间',
    width: 100,
    dataIndex: 'modifiedTime',
    key: 'modifiedTime',
  },
  {
    title: '操作',
    key: 'operation',
    fixed: 'right',
    width: 200,
    render: (text, record, index) => <OperationCell text={text} record={record} index={index} />,
  }
];

const UserTable = (props) => {
  const { editView } = props;
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

  return <Table 
          className={styles.container} 
          columns={columns} 
          rowKey={record => record.id} 
          dataSource={data}
          pagination={false} 
         />;
};

export default connect(mapStateToProps, mapDispatchToProps)(UserTable);