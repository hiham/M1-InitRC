#include <stdio.h>
#include <stdlib.h>

int main() {
    FILE *input_file, *output_file;
    char *string;
    long file_size;
    int index = 0;
    input_file = fopen("C:/Users/Utilisateur/Documents/InitR/APP/M1-InitRC/RC/rc/src/main/resources/53_QURO_PB_3A.txt", "r");
    if (input_file == NULL) {
        printf("Error opening input file.\n");
        return 1;
    }
    fseek(input_file, 0, SEEK_END);
    file_size = ftell(input_file);
    rewind(input_file);
    string = (char *)malloc((file_size + 1) * sizeof(char));
    if (string == NULL) {
        printf("Memory allocation failed.\n");
        fclose(input_file);
        return 1;
    }

    while ((string[index] = fgetc(input_file)) != EOF) {
        index++;
    }
    string[index] = '\0';
    fclose(input_file);
    output_file = fopen("output.pos", "w");
    if (output_file == NULL) {
        printf("Error opening output file.\n");
        free(string);
        return 1;
    }
    fputs(string, output_file);
    fclose(output_file);
    free(string);
    printf("Content successfully copied to output file.\n");

    return 0;
}
