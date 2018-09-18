compile(){
    if [ -z "$OUTPUT_DIR" ]; then
        echo 'var $OUTPUT_DIR doesN''T exist'
    else
        mkdir -p tmp/$OUTPUT_DIR
        YML_NAME=$1
        if [ ! -f $YML_NAME ]; then
            echo "file $YML_NAME doesN''T exist"
        else
            bash ./env-exec.sh env.sh ${YML_NAME} > tmp/${OUTPUT_DIR}/${YML_NAME}
        fi
    fi
}

kube_create(){
    if [ -z "$OUTPUT_DIR" ]; then
        echo 'var $OUTPUT_DIR doesN''T exist'
    else
        YML_NAME=$1
        compile ${YML_NAME}
        kubectl create -f tmp/${OUTPUT_DIR}/${YML_NAME}
    fi
}

kube_replace(){
    if [ -z "$OUTPUT_DIR" ]; then
        echo 'var $OUTPUT_DIR doesN''T exist'
    else
        YML_NAME=$1
        compile ${YML_NAME}
        kubectl replace -f tmp/${OUTPUT_DIR}/${YML_NAME}
    fi
}

