const isMissingCred = (username, password) => {
  return !username || !password;
};

const isValid = (user, password) => {
  if (!user || !password) {
    return false;
  }
  return user.Password === password;
};

const getSignupErr = (username, password, existingUser) => {
  if (isMissingCred(username, password)) {
    return "Username and Password are required";
  }

  if (existingUser) {
    return "Username already exists";
  }

  return null;
};

const buildUrl = (username) => {
  return "/landing?username=" + encodeURIComponent(username);
};

module.exports = { isMissingCred, isValid, getSignupErr, buildUrl };
