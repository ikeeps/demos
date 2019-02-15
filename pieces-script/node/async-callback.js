function resolveAfter2Seconds(number) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve(number);
        }, 2000);
    });
}

printNumber = async (number) => {
    return await resolveAfter2Seconds(number);
};

printNumber(3).then(v => {
    console.log(v);
});