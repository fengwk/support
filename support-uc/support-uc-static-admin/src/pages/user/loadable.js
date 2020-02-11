import React, { Component } from 'react';
import Loadable from 'react-loadable';
import Loading from './my-loading-component'; 

const LoadableComponent = Loadable({
  loader: () => import('./'),
  loading: Loading,
});

export default class Loadable extends Component {
  render() {
    return <LoadableComponent/>;
  }
}