export async function fetchLoginUser(url, email, password){
    url = `${url}users/login`;
    const requestBody = {
        email: email,
        password: password,
    };
    let response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestBody),
    });
    if(!response.ok){
        console.log(response);
        throw new Error("Wrong credentials! 👎");
    }
    return response.json();
}