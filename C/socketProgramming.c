#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <strings.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include "sha1.h" // include this and it'll link to libsha1.a
#define SERVER_PORT 47500
#define FLAG_HELLO ((unsigned char)(0x01 << 7))
#define FLAG_INSTRUCTION ((unsigned char)(0x01 << 6))
#define FLAG_RESPONSE ((unsigned char)(0x01 << 5))
#define FLAG_TERMINATE ((unsigned char)(0x01 << 4))
#define OP_ECHO ((unsigned char)(0x00))
#define OP_INCREMENT ((unsigned char)(0x01))
#define OP_DECREMENT ((unsigned char)(0x02))
#define OP_PUSH ((unsigned char)(0x03))
#define OP_DIGEST ((unsigned char)(0x04))

struct hw_packet {
	unsigned char flag;
	unsigned char operation;
	unsigned short data_len;
	unsigned int seq_num;
	char data[1024];
};
int main(void) {
	FILE *fp;
	struct hostent *hp;
	struct sockaddr_in sin;
	char host[] = "localhost";
	int s;
	int len;
	hp = gethostbyname(host);
	if(!hp) {
		fprintf(stderr, "simplex-talk: unknown host: %s\n", host);
		exit(1);
	}
	bzero((char*)&sin, sizeof(sin));
	sin.sin_family = AF_INET;
	bcopy(hp->h_addr, (char*)&sin.sin_addr, hp->h_length);
	sin.sin_port = htons(SERVER_PORT);
	if((s = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
		perror("simplex-talk: socket\n");
		exit(1);
	}
	if(connect(s, (struct sockaddr*)&sin, sizeof(sin)) < 0) {
		perror("simplex-talk: connect");
		close(s);
		exit(1);
	}
	/* ------------------------- */
	/* so far was the connection */
	/* ------------------------- */
	int studentNumber = 2013150133;
	int *pstudentNumber;
	pstudentNumber = &studentNumber;  
	unsigned int value;
	struct hw_packet buf_struct;
	struct hw_packet buf_struct_rcv;
	char *store = (char*)malloc(pow(2, 20) * sizeof(char)); //
	int offset = 0;
	int total_length = 0;
	char digest[20];
	buf_struct.flag = FLAG_HELLO;
	buf_struct.operation = 0;
	buf_struct.data_len = 4;
	buf_struct.seq_num = 0;
	memcpy(buf_struct.data, pstudentNumber, sizeof(unsigned int));
	buf_struct_rcv.flag = 0;
	buf_struct_rcv.operation = 0;
	buf_struct_rcv.data_len = 0;
	buf_struct_rcv.seq_num = 0;
	send(s, &buf_struct, sizeof(struct hw_packet), 0);
	fprintf(stdout, "Sent first hello message\n\n");
	int termination = 0;
	do {
		recv(s, &buf_struct_rcv, sizeof(struct hw_packet), 0);
		//buf_struct.seq_num = buf_struct_rcv.seq_num;
		if(buf_struct_rcv.flag == FLAG_HELLO) {
			fprintf(stdout, "received hello message with data_len %d and seq_num %d\n", buf_struct_rcv.data_len, buf_struct_rcv.seq_num);
		}
		else if(buf_struct_rcv.flag == FLAG_INSTRUCTION && buf_struct_rcv.operation == OP_ECHO) {
			fprintf(stdout, "received echo message with operation %d, data_len %d and seq_num %d\n", buf_struct_rcv.operation, buf_struct_rcv.data_len, buf_struct_rcv.seq_num);
			buf_struct.flag = FLAG_RESPONSE;
			buf_struct.operation = 0; // this should be set to 0
			buf_struct.data_len = buf_struct_rcv.data_len;
			memcpy(buf_struct.data, buf_struct_rcv.data, sizeof(buf_struct_rcv.data));
			send(s, &buf_struct, sizeof(struct hw_packet), 0);
			fprintf(stdout, "Sent echo message with seq_num %d, data_len %d, data %s to server\n\n", buf_struct.seq_num, buf_struct.data_len, buf_struct.data);
		}
		else if(buf_struct_rcv.flag == FLAG_INSTRUCTION && buf_struct_rcv.operation == OP_INCREMENT) {
			fprintf(stdout, "received increment message with operation %d, data_len %d and seq_num %d\n", buf_struct_rcv.operation, buf_struct_rcv.data_len, buf_struct_rcv.seq_num);
			memcpy(&value, buf_struct_rcv.data, sizeof(unsigned int));
			value += 1;
			buf_struct.flag = FLAG_RESPONSE;
			buf_struct.operation = 0; // this should be set to 0
			buf_struct.data_len = buf_struct_rcv.data_len;
			memcpy(buf_struct.data, &value, sizeof(unsigned int));
			send(s, &buf_struct, sizeof(struct hw_packet), 0);
			fprintf(stdout, "Sent increment message with seq_num %d, data_len %d, data %d to server\n\n", buf_struct.seq_num, buf_struct.data_len, value);
		}
		else if(buf_struct_rcv.flag == FLAG_INSTRUCTION && buf_struct_rcv.operation == OP_DECREMENT) {
			fprintf(stdout, "received decrement message with operation %d, data_len %d and seq_num %d\n", buf_struct_rcv.operation, buf_struct_rcv.data_len, buf_struct_rcv.seq_num);
			memcpy(&value, buf_struct_rcv.data, sizeof(unsigned int));
			value -= 1;
			buf_struct.flag = FLAG_RESPONSE;
			buf_struct.operation = 0; // this should be set to 0
			buf_struct.data_len = buf_struct_rcv.data_len;
			memcpy(buf_struct.data, &value, sizeof(unsigned int));
			send(s, &buf_struct, sizeof(struct hw_packet), 0);
			fprintf(stdout, "Sent decrement message with seq_num %d, data_len %d, data %d to server\n\n", buf_struct.seq_num, buf_struct.data_len, value);
		}
		else if(buf_struct_rcv.flag == FLAG_INSTRUCTION && buf_struct_rcv.operation == OP_PUSH) {
			offset = buf_struct_rcv.seq_num;
			strncpy(store + offset, buf_struct_rcv.data, buf_struct_rcv.data_len);			
			total_length += buf_struct_rcv.data_len;
			buf_struct.flag = FLAG_RESPONSE;
			buf_struct.operation = OP_PUSH;
			buf_struct.seq_num = 0;
			buf_struct.data_len = 0;
			send(s, &buf_struct, sizeof(struct hw_packet), 0);
			int k;
			for(k=0;k<buf_struct_rcv.data_len;k++) {
				fprintf(stdout, "%c", *(store+offset+k));
			}
			fprintf(stdout, "\n");
			fprintf(stdout, "Sent push response\n");
		}
		else if(buf_struct_rcv.flag == FLAG_INSTRUCTION && buf_struct_rcv.operation == OP_DIGEST) {
			SHA1(digest, store, total_length);
			strncpy(buf_struct.data, digest, 20);
			buf_struct.flag = FLAG_RESPONSE;
			buf_struct.operation = OP_DIGEST;
			buf_struct.seq_num = 0;
			buf_struct.data_len = 20;
			send(s, &buf_struct, sizeof(struct hw_packet), 0);
			fprintf(stdout, "Sent digest response\n");
		}
		else if(buf_struct_rcv.flag == FLAG_TERMINATE) {
			fprintf(stdout, "Received termination message\nTerminating connection\n\n");
			termination = 1;
		}
		else {
			fprintf(stdout, "Error. No such flag or operation found\n\n");
			termination = 1; 
		}
	}while(termination != 1);
	close(s);
	return 0;
}
