"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ui = void 0;
exports.ui = {
    toast(msg) {
        console.log('$ui', msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
};
