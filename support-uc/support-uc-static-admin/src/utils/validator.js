const REGEX_EMAIL = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

export const isEmail = (email) => {
  if (!email) {
    return false;
  }
  return REGEX_EMAIL.test(email);
}