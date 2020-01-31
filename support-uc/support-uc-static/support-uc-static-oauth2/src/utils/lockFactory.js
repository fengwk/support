const lockMap = new Map();

class Lock {

  constructor(name) {
    this.name = name;
    this.isLocked = false;
  }

  tryLock() {
    if (this.isLocked) {
      return false;
    } else {
      this.isLocked = true;
      return true;
    }
  }

  unlock() {
    this.isLocked = false;
  }

}

export const createLock = (lockName) => {
  if (lockMap.has(lockName)) {
    return lockMap.get(lockName);
  } else {
    const lock = new Lock(lockName);
    lockMap.set(lockName, lock);
    return lock;
  }
}