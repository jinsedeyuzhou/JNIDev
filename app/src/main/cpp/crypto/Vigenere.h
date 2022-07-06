//
// Created by wyy on 2022/7/6.
//
#ifndef VIGENERE_H
#define VIGENERE_H
#include <string>
#include <iostream>
#include <iterator>
#include <algorithm>
using namespace std;
class Vigenere {
public:
    string plaintext = "";
    string ciphertext = "";
    Vigenere(string keyword) {
        secretKeyWord = keyword;
        initSecretKeyNum();
    }

    string Encrypt(string plaintext) {
        transform(plaintext.begin(), plaintext.end(), plaintext.begin(), tolower);	//È«²¿×ª»»ÎªÐ¡Ð´
        ciphertext = transPlainToCipher(plaintext);
        return ciphertext;
    }

    string Decrypt(string ciphertext) {
        plaintext = transCipherToPlain(ciphertext);
        return plaintext;
    }

private:
    string secretKeyWord = "china";
    int secretKeyNum[5];

    //Get secretKeyNum
    void initSecretKeyNum() {
        for (unsigned int i = 0; i < secretKeyWord.size(); ++i)
        {
            secretKeyNum[i] = secretKeyWord[i] - 'a';
        }
    }

    //Trans plaintext to ciphertext
    string transPlainToCipher(string plaintext) {
        ciphertext = "";
        for (unsigned int i = 0; i < plaintext.size(); ++i)
        {
            if (plaintext[i] != ' ')
            {
                ciphertext += (char)((plaintext[i] - 'a' + secretKeyNum[i % secretKeyWord.size()]) % 26 + 'a');
            }
            else
            {
                ciphertext += ' ';
            }
        }
        return ciphertext;
    }

    //Trans ciphertext to plaintext
    string transCipherToPlain(string ciphertext) {
        plaintext = "";
        for (unsigned int i = 0; i < ciphertext.size(); ++i)
        {
            if (ciphertext[i] != ' ')
            {
                plaintext += (char)((ciphertext[i] - 'a' - secretKeyNum[i % secretKeyWord.size()] + 26) % 26 + 'a');
            }
            else
            {
                plaintext += ' ';
            }
        }
        return plaintext;
    }
};
#endif


