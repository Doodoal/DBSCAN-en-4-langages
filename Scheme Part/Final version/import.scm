#lang scheme

;References
;https://docs.racket-lang.org/reference/pairs.html
(define (readlist filename)
 (call-with-input-file filename
  (lambda (in)
    (read in))))
(define (import)
  (let ((p65 (readlist "partition65.scm"))
        (p74 (readlist "partition74.scm")) 
        (p75 (readlist "partition75.scm"))); J'ai changé "partition74.scm" en "partition75.scm"
                                           ;parce que ça creait des doublons (je ne sais pas si
                                           ;c'etait volontaire ou une erreur donc je prefere vous
                                           ;prevenir afin que vous en teniez compte lors de vos tests
    (append p65 p74 p75)))


;(cluster CLUSTER_ID) CLUSTER_ID est un int
;retourne une liste des differents points faisant partie du cluster
;auquel ce CLUSTER_ID appartient
(define (cluster CLUSTER_ID )(filter
                     (lambda (POINT) ( = (list-ref POINT 4) CLUSTER_ID))
                     (import))  )



;(intersect POINT_ID L)
;Inputs: CLUSTER_ID : int ; L : liste de listes
;retourne la liste d'elements dont le point_id = POINT_ID
;se trouvant dans L
(define (intersect POINT_ID L) (filter
                     (lambda (POINT) ( = (list-ref POINT 1) POINT_ID))
                     L)
  )



  
;(intersection CLUSTER_ID L)
;Inputs CLUSTER_ID : int ; L : liste de listes
;Retourne la liste des groupes ayant les memes points
; que ceux du groupe designe par CLUSTER_ID
;Notez que cette fonction ne marche malheureusement pas (Erreur a l'execution)
(define (intersection CLUSTER_ID L)( (let ((res (list) )
                                           )
                                       (map
                                        (lambda (POINT)
                                          ( append res (intersect (list-ref POINT 1) L))
                                          )
                                        (cluster CLUSTER_ID)
                                        )

                                       )
                                     )
  )
  

               