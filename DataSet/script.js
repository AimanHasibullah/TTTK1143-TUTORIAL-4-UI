class BankAccount {
  static lastAssigned = 1000;
  constructor(balance = 0) {
    BankAccount.lastAssigned++;
    this.accNumber = BankAccount.lastAssigned;
    this.balance = balance;
  }
  getMeasure() { return this.balance; }
  getName() { return `Account #${this.accNumber}`; }
}

class Coin {
  constructor(value, name) {
    this.value = value;
    this.name = name;
  }
  getMeasure() { return this.value; }
  getName() { return this.name; }
}

class Student {
  constructor(name, height) {
    this.name = name;
    this.height = height;
  }
  getMeasure() { return this.height; }
  getName() { return this.name; }
}

class DataSet {
  constructor() {
    this.items = [];
    this.sum = 0;
    this.max = null;
  }

  add(item) {
    this.items.push(item);
    this.sum += item.getMeasure();
    if (this.max === null || item.getMeasure() > this.max.getMeasure()) {
      this.max = item;
    }
  }

  getAverage() {
    return this.items.length === 0 ? 0 : this.sum / this.items.length;
  }

  getMax() {
    return this.max;
  }

  reset() {
    this.items = [];
    this.sum = 0;
    this.max = null;
  }
}

let dataSet = new DataSet();

const itemType = document.getElementById('itemType');
const itemName = document.getElementById('itemName');
const itemMeasure = document.getElementById('itemMeasure');
const addBtn = document.getElementById('addBtn');
const loadPresetBtn = document.getElementById('loadPresetBtn');
const resetBtn = document.getElementById('resetBtn');

const countVal = document.getElementById('countVal');
const avgVal = document.getElementById('avgVal');
const maxNameVal = document.getElementById('maxNameVal');
const maxMeasureVal = document.getElementById('maxMeasureVal');
const itemList = document.getElementById('itemList');

addBtn.addEventListener('click', () => {
  const val = parseFloat(itemMeasure.value);
  if (isNaN(val)) {
    alert('Please enter a valid numeric value for measure.');
    return;
  }

  let item;
  const type = itemType.value;
  const name = itemName.value.trim();

  if (type === 'BankAccount') {
    item = new BankAccount(val);
  } else if (type === 'Coin') {
    item = new Coin(val, name || 'Coin');
  } else {
    item = new Student(name || 'Student', val);
  }

  dataSet.add(item);
  render();
  itemMeasure.value = '';
  itemName.value = '';
});

loadPresetBtn.addEventListener('click', () => {
  dataSet.reset();
  // Load sample coin dataset matching tutorial problem output
  dataSet.add(new Coin(0.25, "Quarter"));
  dataSet.add(new Coin(0.10, "Dime"));
  dataSet.add(new Coin(0.05, "Nickel"));
  render();
});

resetBtn.addEventListener('click', () => {
  dataSet.reset();
  render();
});

function render() {
  countVal.textContent = dataSet.items.length;
  avgVal.textContent = dataSet.getAverage().toFixed(2);

  const maxItem = dataSet.getMax();
  if (maxItem) {
    maxNameVal.textContent = maxItem.getName();
    maxMeasureVal.textContent = maxItem.getMeasure().toFixed(2);
  } else {
    maxNameVal.textContent = 'N/A';
    maxMeasureVal.textContent = '0.00';
  }

  itemList.innerHTML = '';
  dataSet.items.forEach(item => {
    const li = document.createElement('li');
    li.innerHTML = `<span><strong>${item.getName()}</strong></span> <span>${item.getMeasure().toFixed(2)}</span>`;
    itemList.appendChild(li);
  });
}