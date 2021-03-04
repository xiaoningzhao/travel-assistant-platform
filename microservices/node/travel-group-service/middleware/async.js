const asyncHandler = fn => (req, rest, next) =>
    Promise.resolve(fn(req, rest, next)).catch(next);

module.exports = asyncHandler;
