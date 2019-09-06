// a
var boys = ["Peter", "lars", "Ole"];
var girls = ["Janne", "hanne", "Sanne"];
console.log(boys);
// does concat mutate an existing array - No!

// b
var all = boys.concat(girls);
console.log(all);

// c
console.log(all.join());
console.log(all.join(','));
console.log(all.join("-"));

// d
all.push('Lone', 'Gitte');
console.log(all);

// e
all.unshift('Hans', 'Kurt');
console.log(all);

// f
all.shift();
console.log(all);

// g
all.pop();
console.log(all);

// h
all.splice(3, 2);
console.log(all);

// i
all.reverse();
console.log(all);

// j
all.sort();
console.log(all);

// k
all.sort(function (a, b) {
    return a.toLowerCase().localeCompare(b.toLowerCase());
});
console.log(all);

// l
const resultCase = all.map(name => name.toUpperCase());
console.log(resultCase);

// m
const resultL = all.filter(name => name[0] === 'l' || name[0] === 'L');
console.log(resultL);

