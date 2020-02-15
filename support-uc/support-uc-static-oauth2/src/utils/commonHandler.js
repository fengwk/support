function addQuery(redirectUri, code, state) {
  if (redirectUri.indexOf('?') < 0) {
    return redirectUri + '?code=' + code + '&state=' + state;
  } else {
    return redirectUri + '&code=' + code + '&state=' + state;
  }
}

export const handleGrantCodeRedirect = (history, redirectUri, code, state) => {
  const hashIdx = redirectUri.lastIndexOf('#');
  if (hashIdx > 0) {
    const parts = redirectUri.split('#');
    redirectUri = addQuery(parts[0], code, state) + '#' + parts[1];
  } else {
    redirectUri = addQuery(redirectUri, code, state);
  }
  if (redirectUri.indexOf('://') < 0) {
    window.location.href = `${location.origin}${redirectUri ? redirectUri : ''}${location.hash ? location.hash : ''}`;
  } else {
    window.location.href = redirectUri;
  }
}
