var {
  isMissingCred,
  isValid,
  getSignupErr,
  buildUrl,
} = require("./login_helpers");

// isMissingCred
test("both username and password present is not missing", () => {
  expect(isMissingCred("matt", "pass")).toBe(false);
});

test("empty username is missing", () => {
  expect(isMissingCred("", "pass")).toBe(true);
});

test("empty password is missing", () => {
  expect(isMissingCred("matt", "")).toBe(true);
});

test("undefined fields are missing", () => {
  expect(isMissingCred(undefined, undefined)).toBe(true);
});

test("correct password logs in", () => {
  var user = { Username: "matt", Password: "pass" };
  expect(isValid(user, "pass")).toBe(true);
});

test("wrong password does not log in", () => {
  var user = { Username: "matt", Password: "pass" };
  expect(isValid(user, "wrong")).toBe(false);
});

test("password check is case sensitive", () => {
  var user = { Username: "matt", Password: "pass" };
  expect(isValid(user, "PASS")).toBe(false);
});

test("unknown user (no row) does not log in", () => {
  expect(isValid(undefined, "pass")).toBe(false);
});

test("missing password does not log in", () => {
  var user = { Username: "matt", Password: "pass" };
  expect(isValid(user, undefined)).toBe(false);
});

test("new username with password can sign up", () => {
  expect(getSignupErr("newuser", "pw", undefined)).toBe(null);
});

test("sign up without username is rejected", () => {
  expect(getSignupErr("", "pw", undefined)).toBe(
    "Username and Password are required",
  );
});

test("sign up without password is rejected", () => {
  expect(getSignupErr("newuser", "", undefined)).toBe(
    "Username and Password are required",
  );
});

test("sign up with existing username is rejected", () => {
  var existing = { Username: "matt", Password: "pass" };
  expect(getSignupErr("matt", "newpass", existing)).toBe(
    "Username already exists",
  );
});

test("url includes the username", () => {
  expect(buildUrl("matt")).toBe("/landing?username=matt");
});

test("url special characters", () => {
  expect(buildUrl("a b&c")).toBe("/landing?username=a%20b%26c");
});
