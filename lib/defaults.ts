
export function defaults(options, defaults) {
  options = options || {};

  Object.keys(defaults).forEach(function (key) {
    if (typeof options[key] === 'undefined') {
      options[key] = defaults[key];
    }
  })

  return options;
}
