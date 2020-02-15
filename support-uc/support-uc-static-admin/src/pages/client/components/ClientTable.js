import React, { PureComponent, Fragment } from 'react';
import { Table, Input, Button, Modal, Select, Spin, Tag, InputNumber, Radio, Icon } from 'antd';
import { connect } from 'react-redux';
import { fromJS } from 'immutable';
import { actionCreators } from '../store';
import dayjs from 'dayjs';
import styles from './ClientTable.less';

const mapStateToProps = (state) => ({
  clientView: state.getIn(['client', 'clientView']),
  editView: state.getIn(['client', 'editView']),
  loading: state.getIn(['client', 'tableLoading'])
});

const mapDispatchToProps = (dispatch) => ({
  edit(editView, editItem) {
    const newItem = fromJS({ ...editItem.toJS(), editable: true});
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  cancel(editView, editItem, clientView) {
    const id = editItem.get('id');
    if (Number(id) < 0) {
      dispatch(actionCreators.setEditView(editView.delete(id)));
    } else {
      const rollback = clientView.get(editItem.get('id'));
      dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), rollback)));
    }
  },
  save(editView, editItem, clientView) {
    dispatch(actionCreators.save(editView, editItem, clientView));
  },
  changeRowInput(value, editView, editItem, colName) {
    const itemObj = { ...editItem.toJS() };
    itemObj[colName] = value;
    const newItem = fromJS(itemObj);
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  changeNumberRowInput(value, editView, editItem, colName) {
    const itemObj = { ...editItem.toJS() };
    itemObj[colName] = value;
    const newItem = fromJS(itemObj);
    dispatch(actionCreators.setEditView(editView.set(newItem.get('id'), newItem)));
  },
  removeClient({ clientId, editView, clientView }) {
    Modal.confirm({
      content: '确定要删除该客户端么？',
      onOk() {
        return new Promise((resolve, reject) => {
          dispatch(actionCreators.removeClient({ clientId, editView, clientView , resolve }));
        });
      }
    });
  },
  refreshSecret({ clientId, editView, clientView }) {
    dispatch(actionCreators.refreshSecret({ clientId, editView, clientView }));
  },
  closeRedirectRule({ closeIdx, editItem, redirectRules, editView }) {
    const newRedirectRules = redirectRules.delete(closeIdx);
    dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), editItem.set('redirectRules', newRedirectRules))));
  },
  addRedirectRule({ mode, value, editItem, redirectRules, editView }) {
    mode = Number(mode);
    const newRedirectRules = redirectRules.push(fromJS(mode == 1 ? { mode } : { mode, value }));
    dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), editItem.set('redirectRules', newRedirectRules))));
  },
  changeExclusiveMode({ value, editItem, editView }) {
    dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), editItem.set('isExclusive', value == 1 ? false : true))));
  },
  changeDisabledState({ value, editItem, editView }) {
    dispatch(actionCreators.setEditView(editView.set(editItem.get('id'), editItem.set('isDisabled', value == 1 ? false : true))));
  }
});

const RawEditableCell = (props) => {
  const { text, record, index, editView, colName, isNumber=false } = props;
  const { changeRowInput } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  if (editable) {
    return <Input onChange={(e) => { changeRowInput(e.target.value, editView, editItem, colName) }} value={text} />;
  } else {
    return <span>{ text }</span>;
  }
}

const EditableCell = connect(mapStateToProps, mapDispatchToProps)(RawEditableCell);

const RawNumberEditableCell = (props) => {
  const { text, record, index, editView, colName, min, max } = props;
  const { changeNumberRowInput } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  if (editable) {
    return <InputNumber
      min={min}
      max={max}
      onChange={(value) => { changeNumberRowInput(value, editView, editItem, colName) }}
      value={text} />;
  } else {
    return <span>{ text }</span>;
  }
}

const NumberEditableCell = connect(mapStateToProps, mapDispatchToProps)(RawNumberEditableCell);

const RawModeCell = (props) => {
  const { text, record, index, editView, colName, min, max } = props;
  const { changeExclusiveMode } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');

  const isExclusive = Boolean(editItem.get('isExclusive'));

  if (editable) {
    return (
      <Radio.Group onChange={(e) => changeExclusiveMode({ value: e.target.value, editItem, editView })}
                   value={isExclusive ? 2 : 1}>
        <Radio value={1}>共享</Radio>
        <Radio value={2}>排他</Radio>
      </Radio.Group>
    );
  } else {
    return <span>{ isExclusive ? '排他' : '共享' }</span>;
  }
}

const ModeCell = connect(mapStateToProps, mapDispatchToProps)(RawModeCell);

const RawStateCell = (props) => {
  const { text, record, index, editView, colName, min, max } = props;
  const { changeDisabledState } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  const isDisabled = Boolean(editItem.get('isDisabled'));
  if (editable) {
    return (
      <Radio.Group
        onChange={(e) => changeDisabledState({ value: e.target.value, editItem, editView })}
        value={isDisabled ? 2 : 1}>
        <Radio value={1}>启用</Radio>
        <Radio value={2}>禁用</Radio>
      </Radio.Group>
    );
  } else {
    return <span>{ isDisabled ? <Tag color="red">禁用</Tag> : <Tag color="green">启用</Tag> }</span>;
  }
}

const StateCell = connect(mapStateToProps, mapDispatchToProps)(RawStateCell);

class RawRedirectRuleCell extends PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      ruleMode: '1',
      ruleValue: ''
    };
    this.changeRuleMode = this.changeRuleMode.bind(this);
    this.changeRuleValue = this.changeRuleValue.bind(this);
  }

  render() {
    const { text, record, index, editView, colName } = this.props;
    const { changeRowInput, closeRedirectRule, addRedirectRule } = this.props;
    const editItem = editView.get(record.id || record.key);
    const editable = editItem.get('editable');

    const { ruleMode, ruleValue } = this.state;

    const redirectRules = editItem.get('redirectRules');
    const rules = [];

    if (editable) {
      redirectRules.map((item, index) => {
        switch (item.get('mode')) {
          case 1:
            rules.push(<Tag className={styles.ruleTag} closable key={index} onClose={() => { closeRedirectRule({ closeIdx: index, editItem, redirectRules, editView }); }}>任意</Tag>);
            break;
          case 2:
            rules.push(<Tag className={styles.ruleTag} closable key={index} onClose={() => { closeRedirectRule({ closeIdx: index, editItem, redirectRules, editView }); }}>严格 { item.get('value') }</Tag>);
            break;
          case 3:
            rules.push(<Tag className={styles.ruleTag} closable key={index} onClose={() => { closeRedirectRule({ closeIdx: index, editItem, redirectRules, editView }); }}>主机匹配 { item.get('value') }</Tag>);
            break;
          case 4:
            rules.push(<Tag className={styles.ruleTag} closable key={index} onClose={() => { closeRedirectRule({ closeIdx: index, editItem, redirectRules, editView }); }}>前缀匹配 { item.get('value') }</Tag>);
            break;
        }
      });

      let input;
      switch (ruleMode) {
        case '1':
          input = (
            <Button
              type="primary"
              icon="plus"
              onClick={() => { addRedirectRule({ mode: ruleMode, value: ruleValue, editItem, redirectRules, editView }); }}>
            </Button>
          );
          break;
        case '2':
          input = <Input.Search
            style={{width: 265}}
            enterButton={<Icon type="plus" />}
            onChange={this.changeRuleValue}
            value={ruleValue}
            onSearch={() => { addRedirectRule({ mode: ruleMode, value: ruleValue, editItem, redirectRules, editView }); }}
          />;
          break;
        case '3':
          input = <Input.Search
            style={{width: 265}}
            enterButton={<Icon type="plus" />}
            onChange={this.changeRuleValue}
            onSearch={() => { addRedirectRule({ mode: ruleMode, value: ruleValue, editItem, redirectRules, editView }); }}
            value={ruleValue}
          />;
          break;
        case '4':
          input = <Input.Search
            style={{width: 265}}
            enterButton={<Icon type="plus" />}
            onChange={this.changeRuleValue}
            value={ruleValue}
            onSearch={() => { addRedirectRule({ mode: ruleMode, value: ruleValue, editItem, redirectRules, editView }); }}
          />;
          break;
      }

      return (
        <Fragment>
          <div>
            { rules }
          </div>
          <div style={{marginTop: 10}}>
            <Select style={{width: 100}} value={ruleMode} onChange={this.changeRuleMode}>
              <Select.Option value="1">任意</Select.Option>
              <Select.Option value="2">严格</Select.Option>
              <Select.Option value="3">主机匹配</Select.Option>
              <Select.Option value="4">前缀匹配</Select.Option>
            </Select>
            { input }
          </div>
        </Fragment>
      );
    } else {
      redirectRules.map((item, index) => {
        switch (item.get('mode')) {
          case 1:
            rules.push(<Tag className={styles.ruleTag} color="cyan" key={index}>任意</Tag>);
            break;
          case 2:
            rules.push(<Tag className={styles.ruleTag} color="cyan" key={index}>严格 { item.get('value') }</Tag>);
            break;
          case 3:
            rules.push(<Tag className={styles.ruleTag} color="cyan" key={index}>主机匹配 { item.get('value') }</Tag>);
            break;
          case 4:
            rules.push(<Tag className={styles.ruleTag} color="cyan" key={index}>前缀匹配 { item.get('value') }</Tag>);
            break;
        }
      });
      return <Fragment>{ rules }</Fragment>;
    }
  }

  changeRuleMode(ruleMode) {
    this.setState((prevState) => ({ ruleMode }));
  }
  changeRuleValue(e) {
    const v = e.target.value;
    this.setState((prevState) => ({ ruleValue: v }));
  }

}

const RedirectRuleCell = connect(mapStateToProps, mapDispatchToProps)(RawRedirectRuleCell);

const RawOperationCell = (props) => {
  const { text, record, index, editView, clientView } = props;
  const { edit, cancel, save, showClientModal, removeClient, refreshSecret } = props;
  const editItem = editView.get(record.id || record.key);
  const editable = editItem.get('editable');
  const clientId = editItem.get('id');
  if (editable) {
    return (
      <Fragment>
        <Button icon="close-circle"onClick={() => { cancel(editView, editItem, clientView); }}>取消</Button>
        <Button className={styles.rightBtn} icon="check-circle" onClick={() => { save(editView, editItem, clientView); }}>保存</Button>
      </Fragment>
    );
  } else {
    return (
      <Fragment>
        <Button icon="reload" onClick={() => { refreshSecret({ clientId, editView, clientView }); }}>刷新密钥</Button>
        <Button className={styles.rightBtn} icon="edit" onClick={() => { edit(editView, editItem); }}>配置</Button>
        <Button className={styles.rightBtn}  icon="delete" onClick={() => { removeClient({ clientId, editView, clientView }); }} >删除</Button>
      </Fragment>
    );
  }

}

const OperationCell = connect(mapStateToProps, mapDispatchToProps)(RawOperationCell);

const columns = [
  {
    title: '客户端ID',
    width: 100,
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '名称',
    width: 100,
    dataIndex: 'name',
    key: 'name',
    render: (text, record, index) => <EditableCell text={text} record={record} index={index} colName="name" />
  },
  {
    title: '密钥',
    width: 300,
    dataIndex: 'secret',
    key: 'secret',
  },
  {
    title: '重定向规则',
    width: 400,
    dataIndex: 'redirectRules',
    key: 'redirectRules',
    render: (text, record, index) => <RedirectRuleCell text={text} record={record} index={index} />
  },
  {
    title: '访问令牌超时/秒',
    width: 150,
    dataIndex: 'accessExpiresIn',
    key: 'accessExpiresIn',
    render: (text, record, index) => <NumberEditableCell text={text} record={record} index={index} colName="accessExpiresIn" min={1} max={9999999} />
  },
  {
    title: '刷新令牌超时/秒',
    width: 150,
    dataIndex: 'refreshExpiresIn',
    key: 'refreshExpiresIn',
    render: (text, record, index) => <NumberEditableCell text={text} record={record} index={index} colName="refreshExpiresIn" min={1} max={9999999} />
  },
  {
    title: '模式',
    width: 100,
    dataIndex: 'isExclusive',
    key: 'isExclusive',
    render: (text, record, index) => <ModeCell text={text} record={record} index={index} />
  },
  {
    title: '令牌数量限制',
    width: 130,
    dataIndex: 'tokenCountLimit',
    key: 'tokenCountLimit',
    render: (text, record, index) => <NumberEditableCell text={text} record={record} index={index} colName="tokenCountLimit" min={1} max={99} />
  },
  {
    title: '状态',
    width: 130,
    dataIndex: 'isDisabled',
    key: 'isDisabled',
    render: (text, record, index) => <StateCell text={text} record={record} index={index} />
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
    width: 372,
    render: (text, record, index) => <OperationCell text={text} record={record} index={index} />,
  }
];

const ClientTable = (props) => {
  const { editView, loading } = props;
  const data = [];

  editView.map((item) => {
    if (Number(item.get('id')) < 0) {
      // 新增部分
      data.push({
        key: item.get('id'),
        id: '',
        name: item.get('name'),
        secret: item.get('secret'),
        redirectRules: item.get('redirectRules'),
        accessExpiresIn: item.get('accessExpiresIn'),
        refreshExpiresIn: item.get('refreshExpiresIn'),
        isExclusive: item.get('isExclusive'),
        tokenCountLimit: item.get('tokenCountLimit'),
        isDisabled: item.get('isDisabled'),
        createdTime: '',
        modifiedTime: ''
      });
    } else {
      data.push({
        key: item.get('id'),
        id: item.get('id'),
        name: item.get('name'),
        secret: item.get('secret'),
        redirectRules: item.get('redirectRules'),
        accessExpiresIn: item.get('accessExpiresIn'),
        refreshExpiresIn: item.get('refreshExpiresIn'),
        isExclusive: item.get('isExclusive'),
        tokenCountLimit: item.get('tokenCountLimit'),
        isDisabled: item.get('isDisabled'),
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
        scroll={{ x: 2300 }}
       />
    </Fragment>
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(ClientTable);
