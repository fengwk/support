import store from 'store';

let funs = [];

export function registerOnTokenInvail(fun) {
  funs.push(fun);
}

export function tryGetCode() {
  if (location.search && location.search.length > 6) {
    const qs = location.search.split('&');
    for (const q of qs) {
      if (q.indexOf('code') > 0) {
        const items = q.split('=');
        if (items.length == 2) {
          return items[1] ? items[1] : undefined;
        }
      }
    }
  }
  return undefined;
}

export function replaceSearch() {
  let hash = location.hash || '';
  if (hash.indexOf('?') > 0) {
    hash = hash.split('?')[0];
  }
  window.history.replaceState({},'', `${location.origin}${location.pathname ? location.pathname : ''}${hash}`);
}

export function removeAccessToken() {
  store.remove('accessToken');
  for (let fun of funs) {
    fun();
  }
}

export function getAccessToken() {
  return store.get('accessToken');
}

export function setAccessToken(accessToken) {
  store.set('accessToken', accessToken);
}
