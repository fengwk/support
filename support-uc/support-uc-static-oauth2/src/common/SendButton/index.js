import React from 'react';
import { Button } from 'antd';

const SendButton = (props) => {
  const { className, defaultText, isSended, remainingSecond } = props;
  const { onClick } = props;
  return (
    <Button 
      className={className} 
      type="primary" 
      disabled={isSended}
      onClick={onClick}
    >
      { isSended ? ('剩余' + remainingSecond + '秒') : defaultText }
    </Button>
  );
}

export default SendButton;