export const handleGrantCodeRedirect = (history, redirectUri, code, state) => {
  if (redirectUri.indexOf('?') < 0) {
    redirectUri = redirectUri + '?code=' + code + '&state=' + state;
  } else {
    redirectUri = redirectUri + '&code=' + code + '&state=' + state;
  }
  if (redirectUri.indexOf('://') < 0) {
    history.push(redirectUri);
  } else {
    window.location.href = redirectUri;
  }
}