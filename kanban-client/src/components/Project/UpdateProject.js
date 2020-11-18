import React, { Component } from "react";
import { getProject, createProject } from "../../actions/projectActions";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import classnames from "classnames";

class UpdateProject extends Component {

    constructor() {
        super();

        this.state = {
            id: "",
            name: "",
            projectIdentifier: "",
            description: "",
            startDate: "",
            endDate: ""
        };

        //bind functions
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(next) {
        const {
            id,
            name,
            projectIdentifier,
            description,
            startDate,
            endDate
        } = next.project;

        this.setState({
            id,
            name,
            projectIdentifier,
            description,
            startDate,
            endDate
        });
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        this.props.getProject(id, this.props.history);
    }

    onChange(e) {
        this.setState({[e.target.name]:e.target.value});
    }

    onSubmit(e) {
        e.preventDefault();

        const updateProject = {
            id: this.state.id,
            name: this.state.name,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            startDate: this.state.startDate,
            endDate: this.state.endDate
          };
      
        this.props.createProject(updateProject, this.props.history);
    }

    render() {
        return (
        <div className="project">
            <div className="container">
            <div className="row">
                <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Update Project form</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                    <input
                        type="text"
                        className="form-control form-control-lg "
                        placeholder="Project Name"
                        name="name"
                        value={this.state.name}
                        onChange={this.onChange}
                    />
                    </div>
                    <div className="form-group">
                    <input
                        type="text"
                        className="form-control form-control-lg"
                        placeholder="Unique Project ID"
                        name="projectIdentifier"
                        value={this.state.projectIdentifier}
                        onChange={this.onChange}
                        disabled
                    />
                    </div>
                    <div className="form-group">
                    <textarea
                        className="form-control form-control-lg"
                        placeholder="Project Description"
                        name="description"
                        value={this.state.description}
                        onChange={this.onChange}
                    />
                    </div>
                    <h6>Start Date</h6>
                    <div className="form-group">
                    <input
                        type="date"
                        className="form-control form-control-lg"
                        name="startDate"
                        value={this.state.startDate}
                        onChange={this.onChange}
                    />
                    </div>
                    <h6>Estimated End Date</h6>
                    <div className="form-group">
                    <input
                        type="date"
                        className="form-control form-control-lg"
                        name="endDate"
                        value={this.state.endDate}
                        onChange={this.onChange}
                    />
                    </div>

                    <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                    />
                </form>
                </div>
            </div>
            </div>
        </div>
        )
    }
}

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    createProject: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project: state.project.project
});

export default connect(mapStateToProps, {getProject, createProject})(UpdateProject);