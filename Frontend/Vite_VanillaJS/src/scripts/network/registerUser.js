export async function fetchRegisterUser(url, email, password, age, sex){
    url = `${url}users/register`;
    const requestBody = {
        email: email,
        password: password,
        sex: sex,
        age: age,
    };
    let response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
    });
    if(!response.ok){
        throw new Error("Error registering..?");
    }
    return response.json();
}