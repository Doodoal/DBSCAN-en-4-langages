import:-
    csv_read_file('partition65.csv', Data65, [functor(partition)]),maplist(assert, Data65),
    csv_read_file('partition74.csv', Data74, [functor(partition)]),maplist(assert, Data74),
    csv_read_file('partition75.csv', Data75, [functor(partition)]),maplist(assert, Data75),
    csv_read_file('partition76.csv', Data76, [functor(partition)]),maplist(assert, Data76),
    csv_read_file('partition84.csv', Data84, [functor(partition)]),maplist(assert, Data84),
    csv_read_file('partition85.csv', Data85, [functor(partition)]),maplist(assert, Data85),
    csv_read_file('partition86.csv', Data86, [functor(partition)]),maplist(assert, Data86),listing(partition).

%questionnement d import au debut du programme
?-import.




%point/1 decrit la relation entre un POINT_ID et les differents attributs qui lui sont associes
% a l exception de PARTITION_ID

point(POINT_ID,X,Y,CLUSTER_ID):- partition(_,POINT_ID,X,Y,CLUSTER_ID).

%point/2: Simplification de point/1:
% decrit la relation entre un POINT_ID et un CLUSTER_ID qui lui est associe.
point(POINT_ID,CLUSTER_ID):- point(POINT_ID,_,_,CLUSTER_ID).



%duplicate permets de savoir si un POINT_ID est associe a plusieurs CLUSTER_ID
%Exemples:
%duplicate(7030,_) = true
%duplicate(85000005,_)= false

duplicate(POINT_ID,C):- duplicate(POINT_ID,C,_),!.

duplicate(POINT_ID,C,Q):- point(POINT_ID,C), point(POINT_ID, Q), C =\= Q.




%uniqueLabel permets de constituer une liste d attributs unique a un point_ID

%uniqueLabel/1 constitue une liste d attributs a un POINT_ID associe a un CLUSTER_ID est unique
% (donc la liste de ses attributs est deja unique)

uniqueLabel([POINT_ID,X,Y,CLUSTER_ID]):- point(POINT_ID,X,Y,CLUSTER_ID),\+duplicate(POINT_ID,CLUSTER_ID).

%uniqueLabel/2 constitue une liste d attributs a un POINT_ID associe a plusieurs CLUSTER_ID
%Dans ce cas, la liste d attributs contiendra le tout premier CLUSTER_ID trouve par le programme
%et ignorera les autres
uniqueLabel([POINT_ID,X,Y,CLUSTER_ID]):- point(POINT_ID,X,Y,CLUSTER_ID),duplicate(POINT_ID,CLUSTER_ID),!.



%mergeClusters/1 retourne la liste de tous les points avec l etiquette de groupe
%qui leur a ete assignee dans uniqueLabel
mergeClusters(L):- findall([POINT_ID,X,Y,CLUSTER_ID],uniqueLabel([POINT_ID,X,Y,CLUSTER_ID]), L ).



%Tests
test(point):- write('point(7030,Result)'),nl, point(7030,Result),write(Result).
test(duplicate) :- write('duplicate(7030,_) '),nl, duplicate(7030,_).
test(uniqueLabel):-write('uniqueLabel([7030,_,_,Result]).'),nl,uniqueLabel([7030,_,_,Result]),write(Result).
test(mergeClusters):-write("mergeClusters(L),open('clusters.txt',write,F),write(F,L),close(F). "),nl,mergeClusters(L),open('clusters.txt',write,F),write(F,L),close(F),write(L).


%Je propose une autre version test1 qui cette fois ci prends en consideration
%le point 226759 qui a un CLUSTER_ID unique

test1(point):- write('point(226759,Result)'),nl, point(226759,Result),write(Result).
test1(duplicate) :- write('duplicate(226759,_) '),nl, duplicate(226759,_).
test1(uniqueLabel):-write('uniqueLabel([226759,_,_,Result]).'),nl,uniqueLabel([226759,_,_,Result]),write(Result).
