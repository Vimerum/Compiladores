inicio-programa

int x, z;
float y;

x = 5;

ler(x);
ler(y);

mostrar(x);
mostrar(y);
mostrar("Ola mundo");
mostrar(2 + 2);
mostrar(1 + 2 + 3);
mostrar(1 + 2 * 3);
mostrar(x + 3);

se (x > z) {
    se (x > y) {
        mostrar(x);
    } senao {
        mostrar(y);
    }
}
senao {
    mostrar(z);
}

enquanto (x > 5) {
    mostrar(x);
    x = x + 1;
}

faca {
    mostrar(y);
    y = y - 1;
} enquanto (t != 0);
fim-programa
