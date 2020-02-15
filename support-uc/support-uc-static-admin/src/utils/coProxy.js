import co from 'co';
import { createLock } from './lockFactory';

const coProxy = {

  wrap(fun) {
    return (...args) => {
      return new Promise((revolve, reject) => {
        const lockKey = fun.toString();
        const lock = createLock(lockKey);
        if (!lock.tryLock()) {
          revolve();
          return;
        }
        co.wrap(fun)(...args)
          .then((...resolveArgs) => {
            revolve(...resolveArgs);
          })
          .catch((...rejectArgs) => {
            reject(...rejectArgs);
          })
          .finally(() => {
            lock.unlock();
          });
      });
    }
  }

}

export default coProxy;