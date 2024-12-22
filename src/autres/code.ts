


function func(families: string[], sizes: number[]) {
    while (families.length > 1) {
        // console.log(families);
        // console.log(sizes);
        let posMangeur: number[] = []
        let posManger: number[] = []
        for (let i = 0; i < families.length; i++) {
            if ((i - 1 >= 0 && sizes[i] > sizes[i - 1] && families[i] !== families[i - 1])
                &&
                !posManger.some((arg) => arg === i || arg === i - 1)
                &&
                !posMangeur.some((arg) => arg === i || arg === i - 1)
            ) {
                posMangeur.push(i)
                posManger.push(i - 1)
                sizes[i] += sizes[i - 1]
            }
            if ((i + 1 >= families.length && sizes[i] > sizes[i + 1] && families[i] !== families[i + 1])
                &&
                !posManger.some((arg) => arg === i || arg === i + 1)
                &&
                !posMangeur.some((arg) => arg === i || arg === i + 1)
            ) {
                posMangeur.push(i)
                posManger.push(i + 1)
                sizes[i] += sizes[i + 1]
            }
        }
        families = families.filter((arg, i) => !posManger.some(manger => manger === i))
        sizes = sizes.filter((arg, i) => !posManger.some(manger => manger === i))
        // console.log("posMangeur : " + posMangeur);
        // console.log("posManger : " + posManger);
        // console.log(families);
        // console.log(sizes);
        // console.log(families.length);


    }

    return families[0] + " " + sizes[0]
}
let text = func(["A", "B", "C", "B", "A", "C"], [4, 10, 7, 10, 20, 40])
console.log(text)