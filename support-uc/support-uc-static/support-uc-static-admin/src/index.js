import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage.js';
import RegisterPage from './pages/RegisterPage.js';
import ResetPasswordPage from './pages/ResetPasswordPage.js';
import styles from './index.css';

class App extends Component {
    render() {
        return (
            <div>
                <BrowserRouter>
                    <Route path="/" exact component={LoginPage} />
                    <Route path="/register" component={RegisterPage} />
                    <Route path="/resetpwd" component={ResetPasswordPage} />
                </BrowserRouter>
            </div>
        );
    }
}

ReactDOM.render(<App />, document.getElementById('root'));

if (module.hot) {
    module.hot.accept()
}